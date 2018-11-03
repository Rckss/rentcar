import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './price.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPriceDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PriceDetail extends React.Component<IPriceDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { priceEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Price [<b>{priceEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="adjDate">Adj Date</span>
            </dt>
            <dd>
              <TextFormat value={priceEntity.adjDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="name">Name</span>
            </dt>
            <dd>{priceEntity.name}</dd>
            <dt>
              <span id="serial">Serial</span>
            </dt>
            <dd>{priceEntity.serial}</dd>
            <dt>
              <span id="price">Price</span>
            </dt>
            <dd>{priceEntity.price}</dd>
            <dt>
              <span id="factorOne">Factor One</span>
            </dt>
            <dd>{priceEntity.factorOne}</dd>
            <dt>
              <span id="factorTwo">Factor Two</span>
            </dt>
            <dd>{priceEntity.factorTwo}</dd>
            <dt>
              <span id="factorThree">Factor Three</span>
            </dt>
            <dd>{priceEntity.factorThree}</dd>
            <dt>
              <span id="tax">Tax</span>
            </dt>
            <dd>{priceEntity.tax}</dd>
            <dt>
              <span id="total">Total</span>
            </dt>
            <dd>{priceEntity.total}</dd>
            <dt>
              <span id="classification">Classification</span>
            </dt>
            <dd>{priceEntity.classification}</dd>
          </dl>
          <Button tag={Link} to="/entity/price" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/price/${priceEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ price }: IRootState) => ({
  priceEntity: price.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PriceDetail);
