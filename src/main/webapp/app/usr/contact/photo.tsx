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
        <dd>
          {photo.image ? (
            <div>
              <a onClick={openFile(photo.imageContentType, photo.image)}>
                <img src={`data:${photo.imageContentType};base64,${photo.image}`} style={{ maxHeight: '60px' }} />
              </a>
            </div>
          ) : null}
        </dd>
      </div>
    );
  }
}
export default OnePhoto;
