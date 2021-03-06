import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { getSortState, IPaginationBaseState } from 'react-jhipster';
import Gallery from 'react-photo-gallery';
import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './photo.reducer';
// tslint:disable-next-line:no-unused-variable
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IPhotoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IPhotoState = IPaginationBaseState;

export class PhotoG extends React.Component<IPhotoProps, IPhotoState> {
  state: IPhotoState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.reset();
  }

  componentDidUpdate() {
    if (this.props.updateSuccess) {
      this.reset();
    }
  }

  reset = () => {
    this.props.reset();
    this.setState({ activePage: 1 }, () => {
      this.getEntities();
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
    }
  };

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => {
        this.reset();
      }
    );
  };

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { photoList, match } = this.props;
    // added by me
    const photoSet = photoList.map(photo => ({
      src: `data:${photo.imageContentType};base64,${photo.image}`,
      width: photo.height > photo.width ? 3 : photo.height === photo.width ? 1 : 4,
      height: photo.height > photo.width ? 4 : photo.height === photo.width ? 1 : 3
    }));
    // end
    return (
      <div>
        <h2 id="photo-heading">Photos</h2>
        <Gallery photos={photoSet} />
      </div>
    );
  }
}

const mapStateToProps = ({ photo }: IRootState) => ({
  photoList: photo.entities,
  totalItems: photo.totalItems,
  links: photo.links,
  entity: photo.entity,
  updateSuccess: photo.updateSuccess
});

const mapDispatchToProps = {
  getEntities,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PhotoG);
