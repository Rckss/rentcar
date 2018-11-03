import React from 'react';
import { connect } from 'react-redux';
import { Redirect, RouteComponentProps } from 'react-router-dom';

import { IRootState } from 'app/shared/reducers';
import RentModal from './rent-modal';
import { getEntities as getCars } from 'app/entities/car/car.reducer';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { createEntity, getEntity, reset, updateEntity } from './rent-modal.reducer';
// tslint:disable-next-line:no-unused-variable
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILoginProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ILoginState {
  showModal: boolean;
  // isNew: boolean;
  id: string;
}

export class Rent extends React.Component<ILoginProps, ILoginState> {
  state: ILoginState = {
    showModal: this.props.showModal,
    id: this.props.match.params.id
    // isNew: !this.props.match.params || !this.props.match.params.id
  };

  componentDidMount() {
    /*
    if (!this.state.isNew) {
      this.props.getEntity(this.props.match.params.id);
    }
*/
    this.props.getCars();
    this.props.getUsers();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { rentHistoryEntity } = this.props;
      // rentHistoryEntity.status = 'RUNNING';
      const entity = {
        ...rentHistoryEntity,
        ...values,
        cars: mapIdList([this.props.match.params.id])
      };

      /*
      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
      */
      this.props.createEntity(entity);
      this.handleClose();
    }
  };

  componentDidUpdate(prevProps: ILoginProps, prevState) {
    if (this.props !== prevProps) {
      this.setState({ showModal: this.props.showModal });
    }
  }

  handleClose = () => {
    this.props.history.goBack();
    this.setState({ showModal: false });
  };
  // nao se esqueca de passar propriedades
  render() {
    const { rentHistoryEntity, cars, users, loading, updating } = this.props;
    // const { isNew } = this.state;
    const isNew = true;
    const { location, isAuthenticated } = this.props;
    const { from } = location.state || { from: { pathname: '/', search: location.search } };
    const { showModal } = this.state;
    if (!isAuthenticated) {
      return <Redirect to={from} />;
    }
    return (
      <RentModal
        showModal={showModal}
        handleClose={this.handleClose}
        carId={this.props.match.params.id}
        rentHistoryEntity={rentHistoryEntity}
        cars={cars}
        users={users}
        loading={loading}
        updating={updating}
        isNew={isNew}
        saveEntity={this.saveEntity}
      />
    );
  }
}

const mapStateToProps = ({ authentication, car, userManagement, rentModal }: IRootState) => ({
  isAuthenticated: authentication.isAuthenticated,
  showModal: rentModal.showModalLogin,
  cars: car.entities,
  users: userManagement.users,
  rentHistoryEntity: rentModal.entity,
  loading: rentModal.loading,
  updating: rentModal.updating
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
)(Rent);
