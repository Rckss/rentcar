import React from 'react';

import { connect } from 'react-redux';
import { AvField, AvForm } from 'availity-reactstrap-validation';
import { Alert, Button, Col, Row } from 'reactstrap';

import PasswordStrengthBar from 'app/shared/layout/password/password-strength-bar';
import { handleRegister, reset } from './register.reducer';
import { Redirect } from 'react-router';

export type IRegisterProps = DispatchProps;

export interface IRegisterState {
  redirect: boolean;
  password: string;
}

export class RegisterPage extends React.Component<IRegisterProps, IRegisterState> {
  constructor(props) {
    super(props);
    this.state = { redirect: false, password: '' };
  }

  componentWillUnmount() {
    this.props.reset();
  }

  // Submit values to server
  handleValidSubmit = (event, values) => {
    this.props.handleRegister(values.username, values.email, values.firstPassword);
    this.setState({ redirect: true });
    event.preventDefault();
  };

  /* Redirect Contact filling form */

  updatePassword = event => {
    this.setState({ password: event.target.value });
  };

  render() {
    const { redirect } = this.state;
    if (redirect) {
      return <Redirect to="/newcontact/new" />;
    }
    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h1 id="register-title">Registration</h1>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            <AvForm id="register-form" onValidSubmit={this.handleValidSubmit}>
              <AvField
                name="username"
                label="Username"
                placeholder="Your username"
                validate={{
                  required: { value: true, errorMessage: 'Your username is required.' },
                  pattern: { value: '^[_.@A-Za-z0-9-]*$', errorMessage: 'Your username can only contain letters and digits.' },
                  minLength: { value: 1, errorMessage: 'Your username is required to be at least 1 character.' },
                  maxLength: { value: 50, errorMessage: 'Your username cannot be longer than 50 characters.' }
                }}
              />
              <AvField
                name="email"
                label="Email"
                placeholder="Your email"
                type="email"
                validate={{
                  required: { value: true, errorMessage: 'Your email is required.' },
                  minLength: { value: 5, errorMessage: 'Your email is required to be at least 5 characters.' },
                  maxLength: { value: 254, errorMessage: 'Your email cannot be longer than 50 characters.' }
                }}
              />
              <AvField
                name="firstPassword"
                label="New password"
                placeholder="New password"
                type="password"
                onChange={this.updatePassword}
                validate={{
                  required: { value: true, errorMessage: 'Your password is required.' },
                  minLength: { value: 4, errorMessage: 'Your password is required to be at least 4 characters.' },
                  maxLength: { value: 50, errorMessage: 'Your password cannot be longer than 50 characters.' }
                }}
              />
              <PasswordStrengthBar password={this.state.password} />
              <AvField
                name="secondPassword"
                label="New password confirmation"
                placeholder="Confirm the new password"
                type="password"
                validate={{
                  required: { value: true, errorMessage: 'Your confirmation password is required.' },
                  minLength: { value: 4, errorMessage: 'Your confirmation password is required to be at least 4 characters.' },
                  maxLength: { value: 50, errorMessage: 'Your confirmation password cannot be longer than 50 characters.' },
                  match: { value: 'firstPassword', errorMessage: 'The password and its confirmation do not match!' }
                }}
              />
              <Button id="register-submit" color="primary" type="submit">
                Register
              </Button>
            </AvForm>
            <p>&nbsp;</p>
            <Alert color="warning">
              <span>If you want to</span>
              <a className="alert-link"> sign in</a>
              <span>
                , you can try the default accounts:
                <br />- Administrator (login="admin" and password="admin")
                <br />- User (login="user" and password="user").
              </span>
            </Alert>
          </Col>
        </Row>
      </div>
    );
  }
}

const mapDispatchToProps = { handleRegister, reset };
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  null,
  mapDispatchToProps
)(RegisterPage);
