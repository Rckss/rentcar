import React from 'react';
import { openFile } from 'react-jhipster';
import { IPhoto } from 'app/shared/model/photo.model';

export interface IPhotoDetailProps {
  photo: IPhoto;
}

export class OnePhoto extends React.Component<IPhotoDetailProps> {
  render() {
    const { photo } = this.props;
    return (
      <div>
        {photo.image ? (
          <div>
            <a onClick={openFile(photo.imageContentType, photo.image)}>
              <img src={`data:${photo.imageContentType};base64,${photo.image}`} style={{ maxHeight: '300px' }} />
            </a>
          </div>
        ) : null}
      </div>
    );
  }
}
export default OnePhoto;
