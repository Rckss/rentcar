import React from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { getSortState, IPaginationBaseState, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities, reset } from './price.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IPriceProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IPriceState = IPaginationBaseState;

export class Price extends React.Component<IPriceProps, IPriceState> {
  state: IPriceState = {
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
    const { priceList, match } = this.props;
    return (
      <div>
        <h2 id="price-heading">
          Prices
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Price
          </Link>
        </h2>
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
                  <th className="hand" onClick={this.sort('adjDate')}>
                    Adj Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('name')}>
                    Name <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('serial')}>
                    Serial <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('price')}>
                    Price <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('factorOne')}>
                    Factor One <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('factorTwo')}>
                    Factor Two <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('factorThree')}>
                    Factor Three <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('tax')}>
                    Tax <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('total')}>
                    Total <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={this.sort('classification')}>
                    Classification <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {priceList.map((price, i) => (
                  <tr key={`entity-${i}`}>
                    <td>
                      <Button tag={Link} to={`${match.url}/${price.id}`} color="link" size="sm">
                        {price.id}
                      </Button>
                    </td>
                    <td>
                      <TextFormat type="date" value={price.adjDate} format={APP_LOCAL_DATE_FORMAT} />
                    </td>
                    <td>{price.name}</td>
                    <td>{price.serial}</td>
                    <td>{price.price}</td>
                    <td>{price.factorOne}</td>
                    <td>{price.factorTwo}</td>
                    <td>{price.factorThree}</td>
                    <td>{price.tax}</td>
                    <td>{price.total}</td>
                    <td>{price.classification}</td>
                    <td className="text-right">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${price.id}`} color="info" size="sm">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${price.id}/edit`} color="primary" size="sm">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`${match.url}/${price.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ price }: IRootState) => ({
  priceList: price.entities,
  totalItems: price.totalItems,
  links: price.links,
  entity: price.entity,
  updateSuccess: price.updateSuccess
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
)(Price);
