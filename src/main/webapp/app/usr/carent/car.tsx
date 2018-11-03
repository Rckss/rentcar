import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './car.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICarProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export class Car extends React.Component<ICarProps> {
  componentDidMount() {
    this.props.getEntities();
  }

  render() {
    const { carList, match } = this.props;
    return (
      <div>
        <h2 id="car-heading">
          Cars
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Car
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Serial</th>
                <th>Manufacturer</th>
                <th>Model</th>
                <th>Color</th>
                <th>Classification</th>
                <th>Album</th>
                <th>Tag</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {carList.map((car, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${car.id}`} color="link" size="sm">
                      {car.id}
                    </Button>
                  </td>
                  <td>{car.name}</td>
                  <td>{car.serial}</td>
                  <td>{car.manufacturer}</td>
                  <td>
                    <TextFormat type="date" value={car.model} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>{car.color}</td>
                  <td>{car.classification}</td>
                  <td>{car.albumId ? <Link to={`album/${car.albumId}`}>{car.albumId}</Link> : ''}</td>
                  <td>
                    {car.tags
                      ? car.tags.map((val, j) => (
                          <span key={j}>
                            <Link to={`tag/${val.id}`}>{val.name}</Link>
                            {j === car.tags.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${car.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      </div>
    );
  }
}

const mapStateToProps = ({ carent }: IRootState) => ({
  carList: carent.entities
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Car);
