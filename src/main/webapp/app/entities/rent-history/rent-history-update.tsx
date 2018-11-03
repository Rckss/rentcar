import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities as getCars } from 'app/entities/car/car.reducer';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './rent-history.reducer';
// tslint:disable-next-line:no-unused-variable
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRentHistoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IRentHistoryUpdateState {
  isNew: boolean;
  idscar: any[];
  clientId: string;
}

export class RentHistoryUpdate extends React.Component<IRentHistoryUpdateProps, IRentHistoryUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idscar: [],
      clientId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getCars();
    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { rentHistoryEntity } = this.props;
      const entity = {
        ...rentHistoryEntity,
        ...values,
        cars: mapIdList(values.cars)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      this.handleClose();
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/rent-history');
  };

  render() {
    const { rentHistoryEntity, cars, users, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="newprjApp.rentHistory.home.createOrEditLabel">Create or edit a RentHistory</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : rentHistoryEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="rent-history-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="regDateLabel" for="regDate">
                    Reg Date
                  </Label>
                  <AvField id="rent-history-regDate" type="date" className="form-control" name="regDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="startDateLabel" for="startDate">
                    Start Date
                  </Label>
                  <AvField id="rent-history-startDate" type="date" className="form-control" name="startDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="endDateLabel" for="endDate">
                    End Date
                  </Label>
                  <AvField id="rent-history-endDate" type="date" className="form-control" name="endDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalPaidLabel" for="totalPaid">
                    Total Paid
                  </Label>
                  <AvField id="rent-history-totalPaid" type="string" className="form-control" name="totalPaid" />
                </AvGroup>
                <AvGroup>
                  <Label id="statusLabel">Status</Label>
                  <AvInput
                    id="rent-history-status"
                    type="select"
                    className="form-control"
                    name="status"
                    value={(!isNew && rentHistoryEntity.status) || 'RUNNING'}
                  >
                    <option value="RUNNING">RUNNING</option>
                    <option value="PENDING">PENDING</option>
                    <option value="PAYED">PAYED</option>
                    <option value="CANCELLED">CANCELLED</option>
                    <option value="DELETED">DELETED</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="cars">Car</Label>
                  <AvInput
                    id="rent-history-car"
                    type="select"
                    multiple
                    className="form-control"
                    name="cars"
                    value={rentHistoryEntity.cars && rentHistoryEntity.cars.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {cars
                      ? cars.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="client.id">Client</Label>
                  <AvInput id="rent-history-client" type="select" className="form-control" name="clientId">
                    <option value="" key="0" />
                    {users
                      ? users.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/rent-history" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  cars: storeState.car.entities,
  users: storeState.userManagement.users,
  rentHistoryEntity: storeState.rentHistory.entity,
  loading: storeState.rentHistory.loading,
  updating: storeState.rentHistory.updating
});

const mapDispatchToProps = {
  getCars,
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RentHistoryUpdate);
