import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const ConfigurationsMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Configurations" id="entity-menu">
    <DropdownItem tag={Link} to="/configurations/car">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Car
    </DropdownItem>
    <DropdownItem tag={Link} to="/configurations/contact">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Contact
    </DropdownItem>
    <DropdownItem tag={Link} to="/configurations/rent-history">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Rent History
    </DropdownItem>
    <DropdownItem tag={Link} to="/configurations/album">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Album
    </DropdownItem>
    <DropdownItem tag={Link} to="/configurations/photo">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Photo
    </DropdownItem>
    <DropdownItem tag={Link} to="/configurations/tag">
      <FontAwesomeIcon icon="asterisk" />
      &nbsp;Tag
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
