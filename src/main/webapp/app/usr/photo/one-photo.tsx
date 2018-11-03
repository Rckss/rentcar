import React from 'react';
import { connect } from 'react-redux';
import { RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { openFile } from 'react-jhipster';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './photo.reducer';

// tslint:disable-next-line:no-unused-variable

export interface IPhotoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class PhotoDetail extends React.Component<IPhotoDetailProps> {
  componentWillMount() {
    // need to get photo by albumId of respective car and photoId
    this.props.getEntity(String(this.props.match.params.id));
  }

  render() {
    const { photoEntity } = this.props;
    return (
      <div>
        {photoEntity.image ? (
          <div>
            <a onClick={openFile(photoEntity.imageContentType, photoEntity.image)}>
              <img src={`data:${photoEntity.imageContentType};base64,${photoEntity.image}`} style={{ maxHeight: '300px' }} />
            </a>
          </div>
        ) : null}
      </div>
    );
  }
}

const mapStateToProps = ({ photo }: IRootState) => ({
  photoEntity: photo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(PhotoDetail);
