import React from 'react';

import { Alert, Button, Col, Label, Modal, ModalBody, ModalFooter, ModalHeader, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Link } from 'react-router-dom';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

export interface ILoginModalProps {
  showModal: boolean;
  handleClose: Function;
  saveEntity: Function;
  rentHistoryEntity: any;
  cars: any;
  users: any;
  loading: any;
  updating: any;
  isNew: boolean;
  // id: string; // no need
  carId: string;
}

class RentModal extends React.Component<ILoginModalProps> {
  render() {
    const { handleClose, saveEntity, carId } = this.props;
    const { rentHistoryEntity, cars, users, loading, updating } = this.props;
    const { isNew } = this.props;
    return (
      <Modal isOpen={this.props.showModal} toggle={handleClose} backdrop="static" id="login-page" autoFocus={false}>
        <ModalHeader id="login-title" toggle={handleClose}>
          When to Rent?
        </ModalHeader>
        <ModalBody>
          <Row className="justify-content-center">
            <Col md="8">
              {loading ? (
                <p>Loading...</p>
              ) : (
                <AvForm model={isNew ? {} : rentHistoryEntity} onSubmit={saveEntity}>
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
                    <Label for="carId">Car to rent</Label>
                    <AvInput id="carId" type="text" className="form-control" name="carId" value={carId} required readOnly />
                  </AvGroup>
                  <Button tag={Link} id="cancel-save" to="" onClick={handleClose} replace color="info">
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
        </ModalBody>
      </Modal>
    );
  }
}

export default RentModal;
