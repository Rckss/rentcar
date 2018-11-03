import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Photo from './photo';
import ListOfPhotos from './listofphotos';
import PhotoG from './photogallery';
import PhotoDetail from './photo-detail';
import PhotoUpdate from './photo-update';
import PhotoDeleteDialog from './photo-delete-dialog';
import OnePhoto from './one-photo';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute path={`${match.url}/gallery`} component={PhotoG} />
      <ErrorBoundaryRoute path={`${match.url}/list`} component={ListOfPhotos} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PhotoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PhotoUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PhotoDetail} />
      <ErrorBoundaryRoute exact path={`${match.url}/a/:id`} component={OnePhoto} />
      <ErrorBoundaryRoute path={match.url} component={Photo} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={PhotoDeleteDialog} />
  </>
);

export default Routes;
