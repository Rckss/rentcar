import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './album.reducer';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT } from 'app/config/constants';

export interface IAlbumDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class AlbumDetail extends React.Component<IAlbumDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { albumEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Album [<b>{albumEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Title</span>
            </dt>
            <dd>{albumEntity.title}</dd>
            <dt>
              <span id="description">Description</span>
            </dt>
            <dd>{albumEntity.description}</dd>
            <dt>
              <span id="created">Created</span>
            </dt>
            <dd>
              <TextFormat value={albumEntity.created} type="date" format={APP_DATE_FORMAT} />
            </dd>
          </dl>
          <Button tag={Link} to="/configurations/album" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/configurations/album/${albumEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
          <Button tag={Link} to={`/configurations/album/photosperalbum/list/${albumEntity.id}`} replace color="danger">
            <span className="d-none d-md-inline">View Album</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ album }: IRootState) => ({
  albumEntity: album.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(AlbumDetail);
