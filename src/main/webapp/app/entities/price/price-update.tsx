import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { createEntity, getEntity, reset, updateEntity } from './price.reducer';

// tslint:disable-next-line:no-unused-variable

export interface IPriceUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IPriceUpdateState {
  isNew: boolean;
}

export class PriceUpdate extends React.Component<IPriceUpdateProps, IPriceUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { priceEntity } = this.props;
      const entity = {
        ...priceEntity,
        ...values
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
    this.props.history.push('/entity/price');
  };

  render() {
    const { priceEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="newprjApp.price.home.createOrEditLabel">Create or edit a Price</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : priceEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="price-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="adjDateLabel" for="adjDate">
                    Adj Date
                  </Label>
                  <AvField id="price-adjDate" type="date" className="form-control" name="adjDate" />
                </AvGroup>
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="price-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="serialLabel" for="serial">
                    Serial
                  </Label>
                  <AvField id="price-serial" type="text" name="serial" />
                </AvGroup>
                <AvGroup>
                  <Label id="priceLabel" for="price">
                    Price
                  </Label>
                  <AvField id="price-price" type="string" className="form-control" name="price" />
                </AvGroup>
                <AvGroup>
                  <Label id="factorOneLabel" for="factorOne">
                    Factor One
                  </Label>
                  <AvField id="price-factorOne" type="string" className="form-control" name="factorOne" />
                </AvGroup>
                <AvGroup>
                  <Label id="factorTwoLabel" for="factorTwo">
                    Factor Two
                  </Label>
                  <AvField id="price-factorTwo" type="string" className="form-control" name="factorTwo" />
                </AvGroup>
                <AvGroup>
                  <Label id="factorThreeLabel" for="factorThree">
                    Factor Three
                  </Label>
                  <AvField id="price-factorThree" type="string" className="form-control" name="factorThree" />
                </AvGroup>
                <AvGroup>
                  <Label id="taxLabel" for="tax">
                    Tax
                  </Label>
                  <AvField id="price-tax" type="string" className="form-control" name="tax" />
                </AvGroup>
                <AvGroup>
                  <Label id="totalLabel" for="total">
                    Total
                  </Label>
                  <AvField id="price-total" type="string" className="form-control" name="total" />
                </AvGroup>
                <AvGroup>
                  <Label id="classificationLabel">Classification</Label>
                  <AvInput
                    id="price-classification"
                    type="select"
                    className="form-control"
                    name="classification"
                    value={(!isNew && priceEntity.classification) || 'AAA'}
                  >
                    <option value="AAA">AAA</option>
                    <option value="BBB">BBB</option>
                    <option value="CCC">CCC</option>
                    <option value="DDD">DDD</option>
                    <option value="EEE">EEE</option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/price" replace color="info">
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
  priceEntity: storeState.price.entity,
  loading: storeState.price.loading,
  updating: storeState.price.updating
});

const mapDispatchToProps = {
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
)(PriceUpdate);
