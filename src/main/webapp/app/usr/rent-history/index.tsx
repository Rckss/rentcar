import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import RentHistory from './rent-history';
import RentHistoryDetail from './rent-history-detail';
import RentHistoryUpdate from './rent-history-update';
import RentHistoryDeleteDialog from './rent-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RentHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RentHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RentHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={RentHistory} />
    </Switch>
    <ErrorBoundaryRoute path={`${match.url}/:id/delete`} component={RentHistoryDeleteDialog} />
  </>
);

export default Routes;
