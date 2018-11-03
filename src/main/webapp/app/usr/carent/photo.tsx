import React from 'react';
import { byteSize, openFile } from 'react-jhipster';
import { IPhoto } from 'app/shared/model/photo.model';

export interface IPhotoDetailProps {
  photo: IPhoto;
  getPhoto: any;
  photoId: number;
}

export class OnePhoto extends React.Component<IPhotoDetailProps> {
  componentDidMount() {
    this.props.getPhoto(this.props.photoId);
  }
  render() {
    const { photo } = this.props;
    return (
      <div>
        <dt>
          <span id="title">Title</span>
        </dt>
        <dd>{photo.title}</dd>
        <dt>
          <span id="description">Description</span>
        </dt>
        <dd>{photo.description}</dd>
        <dt>
          <span id="image">Image</span>
        </dt>
        <dd>
          {photo.image ? (
            <div>
              <a onClick={openFile(photo.imageContentType, photo.image)}>
                <img src={`data:${photo.imageContentType};base64,${photo.image}`} style={{ maxHeight: '30px' }} />
              </a>
              <span>
                {photo.imageContentType}, {byteSize(photo.image)}
              </span>
            </div>
          ) : null}
        </dd>
      </div>
    );
  }
}
export default OnePhoto;
