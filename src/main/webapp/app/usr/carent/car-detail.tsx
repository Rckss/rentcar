import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './car.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { OnePhoto } from './photo';
import { getEntity as getPhoto } from 'app/usr/photo/photo.reducer';

export interface ICarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class CarDetail extends React.Component<ICarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }
  /* don't forget:
    -make show first album photo as main photo
    and display rent button
  */

  render() {
    const { carEntity, photoEntity } = this.props;
    if (carEntity != null) {
      return (
        <Row>
          <Col md="8">
            <dl className="jh-entity-details">
              <dt>
                <span id="name">
                  <h2>{carEntity.name}</h2>
                </span>
              </dt>
              <dt>
                <span id="serial">Serial</span>
              </dt>
              <dd>{carEntity.serial}</dd>
              <dt>
                <span id="manufacturer">Manufacturer</span>
              </dt>
              <dd>{carEntity.manufacturer}</dd>
              <dt>
                <span id="model">Model</span>
              </dt>
              <dd>
                <TextFormat value={carEntity.model} type="date" format={APP_LOCAL_DATE_FORMAT} />
              </dd>
              <dt>
                <span id="color">Color</span>
              </dt>
              <dd>{carEntity.color}</dd>
              <dt>
                <span id="classification">Classification</span>
              </dt>
              <dd>{carEntity.classification}</dd>
              <dt>Album</dt>
              <dd>{carEntity.albumId ? carEntity.albumId : ''}</dd>
              <dt>Tag</dt>
              <dd>
                {carEntity.tags
                  ? carEntity.tags.map((val, i) => (
                      <span key={val.id}>
                        <a>{val.name}</a>
                        {i === carEntity.tags.length - 1 ? '' : ', '}
                      </span>
                    ))
                  : null}
              </dd>
              <dt>Photo</dt>
              <dd>{carEntity.photoId ? carEntity.photoId : ''}</dd>
              {carEntity.photoId ? <OnePhoto photo={photoEntity} photoId={carEntity.photoId} getPhoto={this.props.getPhoto} /> : ''}
            </dl>
            <Button tag={Link} to="/configurations/carent" replace color="info">
              <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
            </Button>
            &nbsp;
            <Button tag={Link} to={`${this.props.match.params.id}/rent`} replace color="danger">
              <span className="d-none d-md-inline">Rent</span>
            </Button>
            &nbsp;
          </Col>
        </Row>
      );
    } else {
      return <h1>None:(</h1>;
    }
  }
}

const mapStateToProps = ({ carent, photo }: IRootState) => ({
  carEntity: carent.entity,
  photoEntity: photo.entity
});

const mapDispatchToProps = { getEntity, getPhoto };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(CarDetail);
