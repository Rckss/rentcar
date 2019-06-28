import React from 'react';
import { Button, Col, Alert, Row } from 'reactstrap';
import { connect } from 'react-redux';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { AvForm, AvField } from 'availity-reactstrap-validation';

import { IRootState } from 'app/shared/reducers';
import { getSession } from 'app/shared/reducers/authentication';
import { saveAccountSettings, reset, fetchUserAndPhoto } from './settings.reducer';
import { getEntities, reset as resetPhotos } from 'app/usr/photo/photo.reducer';
import { IPhoto } from 'app/shared/model/photo.model';
import { Link, RouteComponentProps } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IUserSettingsProps extends StateProps, DispatchProps {}

export interface IUserSettingsState {
  account: any;
  photoEntity: IPhoto[];
}

export class SettingsPage extends React.Component<IUserSettingsProps, IUserSettingsState> {
  componentDidMount() {
    this.props.fetchUserAndPhoto();
  }

  // Why not just using the componentWillReceiveProps React lifecycle method ?
  /*
  componentWillReceiveProps() {
    if (this.props.account && this.props.photoEntities) {
      this.props.photoEntity = photoEntities.data[0];
      // console.log(this.props);
    }
  }
*/
  componentWillUnmount() {
    this.props.reset();
    this.props.resetPhotos();
  }

  handleValidSubmit = (event, values) => {
    const account = {
      ...this.props.account,
      ...values
    };

    this.props.saveAccountSettings(account);
    event.persist();
  };

  // modificar depois:
  // voce precisa alterar para mostrar a foto registrada no contact table relacionado ao usuario especifico
  // reutilizar codigo da pasta: "C:\Users\rcksk\myprojects\kent\newprj\src\main\webapp\app\usr\carent"
  render() {
    const { account, photoEntity } = this.props;
    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="settings-title">User settings for {account.login}</h2>
            <AvForm id="settings-form" onValidSubmit={this.handleValidSubmit}>
              <div>
                {photoEntity.map((photo, i) => (
                  <div key={`entity-${i}`}>
                    <div>
                      {photo.image ? (
                        <div>
                          <a onClick={openFile(photo.imageContentType, photo.image)}>
                            <img src={`data:${photo.imageContentType};base64,${photo.image}`} style={{ maxHeight: '60px' }} />
                            &nbsp;
                          </a>
                        </div>
                      ) : null}
                    </div>
                  </div>
                ))}
              </div>
              {/* First name */}
              <AvField
                className="form-control"
                name="firstName"
                label="First Name"
                id="firstName"
                placeholder="Your first name"
                validate={{
                  required: { value: true, errorMessage: 'Your first name is required.' },
                  minLength: { value: 1, errorMessage: 'Your first name is required to be at least 1 character' },
                  maxLength: { value: 50, errorMessage: 'Your first name cannot be longer than 50 characters' }
                }}
                value={account.firstName}
              />
              {/* Last name */}
              <AvField
                className="form-control"
                name="lastName"
                label="Last Name"
                id="lastName"
                placeholder="Your last name"
                validate={{
                  required: { value: true, errorMessage: 'Your last name is required.' },
                  minLength: { value: 1, errorMessage: 'Your last name is required to be at least 1 character' },
                  maxLength: { value: 50, errorMessage: 'Your last name cannot be longer than 50 characters' }
                }}
                value={account.lastName}
              />
              {/* Email */}
              <AvField
                name="email"
                label="Email"
                placeholder="Your email"
                type="email"
                validate={{
                  required: { value: true, errorMessage: 'Your email is required.' },
                  minLength: { value: 5, errorMessage: 'Your email is required to be at least 5 characters.' },
                  maxLength: { value: 254, errorMessage: 'Your email cannot be longer than 50 characters.' }
                }}
                value={account.email}
              />
              <Button color="primary" type="submit">
                Save
              </Button>
            </AvForm>
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ authentication, photo }: IRootState) => ({
  account: authentication.account,
  isAuthenticated: authentication.isAuthenticated,
  photoEntity: photo.entities
});

const mapDispatchToProps = { getSession, saveAccountSettings, reset, getEntities, fetchUserAndPhoto, resetPhotos };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(SettingsPage);
