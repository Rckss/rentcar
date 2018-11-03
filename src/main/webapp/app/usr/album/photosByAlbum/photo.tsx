import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { byteSize, getSortState, IPaginationBaseState, openFile, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import Gallery from 'react-photo-gallery';
import { IRootState } from 'app/shared/reducers';
import { getEntities, getEntitiesById, reset } from './photo.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IPhotoProps extends StateProps, DispatchProps, RouteComponentProps<{ albumId: string; url: string }> {}

export type IPhotoState = IPaginationBaseState;

export class Photo extends React.Component<IPhotoProps, IPhotoState> {
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
      // this.getEntities();
      this.getEntitiesById(this.props.match.params.albumId);
    });
  };

  handleLoadMore = () => {
    if (window.pageYOffset > 0) {
      // this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntities());
      this.setState({ activePage: this.state.activePage + 1 }, () => this.getEntitiesById(this.props.match.params.albumId));
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

  getEntitiesById = (albumId: string | number) => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntitiesById(albumId, activePage - 1, itemsPerPage, `${sort},${order}`);
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
        <h2 id="photo-heading">
          Photos
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Photo
          </Link>
        </h2>
        <Gallery photos={photoSet} />
        <div className="table-responsive">
          <InfiniteScroll
            pageStart={this.state.activePage}
            loadMore={this.handleLoadMore}
            hasMore={this.state.activePage - 1 < this.props.links.next}
            loader={<div className="loader">Loading ...</div>}
            threshold={0}
            initialLoad={false}
          >
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={this.sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('title')}>
                    Title <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('description')}>
                    Description <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('image')}>
                    Image <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('height')}>
                    Height <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('width')}>
                    Width <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('taken')}>
                    Taken <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('uploaded')}>
                    Uploaded <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    User <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Album <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {photoList.map((photo, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${photo.id}`} color="link" size="sm">
                        {photo.id}
                      </Button>
                    </td>
                    <td>{photo.title}</td>
                    <td>{photo.description}</td>
                    <td>
                      {photo.image ? (
                        <div>
                          <a onClick={openFile(photo.imageContentType, photo.image)}>
                            <img src={`data:${photo.imageContentType};base64,${photo.image}`} style={{ maxHeight: '30px' }} />
                            &nbsp;
                          </a>
                          <span>
                            {photo.imageContentType}, {byteSize(photo.image)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{photo.height}</td>
                    <td>{photo.width}</td>
                    <td>
                      <TextFormat type="date" value={photo.taken} format={APP_DATE_FORMAT} />
                    </td>
                    <td>
                      <TextFormat type="date" value={photo.uploaded} format={APP_DATE_FORMAT} />
                    </td>
                    <td>{photo.userId ? photo.userId : ''}</td>
                    <td>{photo.albumTitle ? <Link to={`album/${photo.albumId}`}>{photo.albumTitle}</Link> : ''}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${photo.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${photo.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${photo.id}/delete`} color="danger" size="sm">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </InfiniteScroll>
        </div>
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
  getEntitiesById,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Photo);
