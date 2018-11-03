import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './contact.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IContactDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ContactDetail extends React.Component<IContactDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { contactEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Contact [<b>{contactEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="phoneNumber">Phone Number</span>
            </dt>
            <dd>{contactEntity.phoneNumber}</dd>
            <dt>
              <span id="birthday">Birthday</span>
            </dt>
            <dd>
              <TextFormat value={contactEntity.birthday} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="addressOne">Address One</span>
            </dt>
            <dd>{contactEntity.addressOne}</dd>
            <dt>
              <span id="addressTwo">Address Two</span>
            </dt>
            <dd>{contactEntity.addressTwo}</dd>
            <dt>
              <span id="postalCode">Postal Code</span>
            </dt>
            <dd>{contactEntity.postalCode}</dd>
            <dt>
              <span id="city">City</span>
            </dt>
            <dd>{contactEntity.city}</dd>
            <dt>
              <span id="stateProvince">State Province</span>
            </dt>
            <dd>{contactEntity.stateProvince}</dd>
            <dt>
              <span id="countryName">Country Name</span>
            </dt>
            <dd>{contactEntity.countryName}</dd>
            <dt>Client</dt>
            <dd>{contactEntity.clientId ? contactEntity.clientId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/contact" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/contact/${contactEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ contact }: IRootState) => ({
  contactEntity: contact.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ContactDetail);
