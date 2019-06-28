// not working :( noway to know what is the shiit problem of this route
// I give up this.
import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './fcar.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { OnePhoto } from './photo';

export interface ICarDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class Details extends React.Component<ICarDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }
  /* don't forget:
    -make show first album photo as main photo
    and display rent button
  */

  render() {
    const { carEntity } = this.props;
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
              <dd>
                <OnePhoto photo={carEntity} />
              </dd>
            </dl>
            <Button tag={Link} to="/configurations/car" replace color="info">
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

const mapStateToProps = ({ fcar }: IRootState) => ({
  carEntity: fcar.entity
});

const mapDispatchToProps = {
  getEntity
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Details);
