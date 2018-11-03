import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './rent-history.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRentHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class RentHistoryDetail extends React.Component<IRentHistoryDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { rentHistoryEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            RentHistory [<b>{rentHistoryEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="regDate">Reg Date</span>
            </dt>
            <dd>
              <TextFormat value={rentHistoryEntity.regDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="startDate">Start Date</span>
            </dt>
            <dd>
              <TextFormat value={rentHistoryEntity.startDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">End Date</span>
            </dt>
            <dd>
              <TextFormat value={rentHistoryEntity.endDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="totalPaid">Total Paid</span>
            </dt>
            <dd>{rentHistoryEntity.totalPaid}</dd>
            <dt>
              <span id="status">Status</span>
            </dt>
            <dd>{rentHistoryEntity.status}</dd>
            <dt>Car</dt>
            <dd>
              {rentHistoryEntity.cars
                ? rentHistoryEntity.cars.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === rentHistoryEntity.cars.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
            <dt>Client</dt>
            <dd>{rentHistoryEntity.clientId ? rentHistoryEntity.clientId : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/rent-history" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/rent-history/${rentHistoryEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ rentHistory }: IRootState) => ({
  rentHistoryEntity: rentHistory.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(RentHistoryDetail);
