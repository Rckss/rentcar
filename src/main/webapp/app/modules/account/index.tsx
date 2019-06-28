import React from 'react';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Settings from './settings/settings';
import Profile from './profile/settings';
import Password from './password/password';

const Routes = ({ match }) => (
  <div>
    <ErrorBoundaryRoute path={`${match.url}/settings`} component={Settings} />
    <ErrorBoundaryRoute path={`${match.url}/profile`} component={Profile} />
    <ErrorBoundaryRoute path={`${match.url}/password`} component={Password} />
  </div>
);

export default Routes;
