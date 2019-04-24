import React from 'react';
import { Link } from 'react-router-dom';
import HeaderList from './components/HeaderList'

class Header extends React.Component {
  render() {
    return (
      <nav className="navbar navbar-light">
        <div className="container">

          <Link to="/" className="navbar-brand">
            {this.props.appName.toLowerCase()}
          </Link>
          <HeaderList currentUser={this.props.currentUser} logout={this.props.onClickLogout} />
        </div>
      </nav>
    );
  }
}

export default Header;
