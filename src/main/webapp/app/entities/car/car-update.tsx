import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Label, Row } from 'reactstrap';
import { AvField, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getEntities as getAlbums } from 'app/entities/album/album.reducer';
import { getEntities as getTags } from 'app/entities/tag/tag.reducer';
import { getEntities as getPhotos } from 'app/entities/photo/photo.reducer';
import { createEntity, getEntity, reset, updateEntity } from './car.reducer';
// tslint:disable-next-line:no-unused-variable
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICarUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ICarUpdateState {
  isNew: boolean;
  idstag: any[];
  albumId: string;
  photoId: string;
}

export class CarUpdate extends React.Component<ICarUpdateProps, ICarUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idstag: [],
      albumId: '0',
      photoId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getAlbums();
    this.props.getTags();
    this.props.getPhotos();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { carEntity } = this.props;
      const entity = {
        ...carEntity,
        ...values,
        tags: mapIdList(values.tags)
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
    this.props.history.push('/entity/car');
  };

  render() {
    const { carEntity, albums, tags, photos, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="newprjApp.car.home.createOrEditLabel">Create or edit a Car</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : carEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="car-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="name">
                    Name
                  </Label>
                  <AvField id="car-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="serialLabel" for="serial">
                    Serial
                  </Label>
                  <AvField id="car-serial" type="text" name="serial" />
                </AvGroup>
                <AvGroup>
                  <Label id="manufacturerLabel" for="manufacturer">
                    Manufacturer
                  </Label>
                  <AvField id="car-manufacturer" type="text" name="manufacturer" />
                </AvGroup>
                <AvGroup>
                  <Label id="modelLabel" for="model">
                    Model
                  </Label>
                  <AvField id="car-model" type="date" className="form-control" name="model" />
                </AvGroup>
                <AvGroup>
                  <Label id="colorLabel" for="color">
                    Color
                  </Label>
                  <AvField id="car-color" type="text" name="color" />
                </AvGroup>
                <AvGroup>
                  <Label id="classificationLabel">Classification</Label>
                  <AvInput
                    id="car-classification"
                    type="select"
                    className="form-control"
                    name="classification"
                    value={(!isNew && carEntity.classification) || 'AAA'}
                  >
                    <option value="AAA">AAA</option>
                    <option value="BBB">BBB</option>
                    <option value="CCC">CCC</option>
                    <option value="DDD">DDD</option>
                    <option value="EEE">EEE</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="album.id">Album</Label>
                  <AvInput id="car-album" type="select" className="form-control" name="albumId">
                    <option value="" key="0" />
                    {albums
                      ? albums.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="tags">Tag</Label>
                  <AvInput
                    id="car-tag"
                    type="select"
                    multiple
                    className="form-control"
                    name="tags"
                    value={carEntity.tags && carEntity.tags.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {tags
                      ? tags.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="photo.id">Photo</Label>
                  <AvInput id="car-photo" type="select" className="form-control" name="photoId">
                    <option value="" key="0" />
                    {photos
                      ? photos.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/car" replace color="info">
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
  albums: storeState.album.entities,
  tags: storeState.tag.entities,
  photos: storeState.photo.entities,
  carEntity: storeState.car.entity,
  loading: storeState.car.loading,
  updating: storeState.car.updating
});

const mapDispatchToProps = {
  getAlbums,
  getTags,
  getPhotos,
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
)(CarUpdate);
