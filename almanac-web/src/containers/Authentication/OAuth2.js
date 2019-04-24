import React, { Component } from 'react';
import Popup                from './Popup';
import qs                   from 'querystring';

class OAuth2 extends Component {
  constructor (props) {
    super(props);
    this.state = { popupOpen: false };
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick () {
    this.setState({ popupOpen: true });
    console.log('clicked on button');
  }

  render () {
    const props = this.props;

    const childrenWithProps = React.Children.map(props.children, (child) => {
      return React.cloneElement(child, { onClick: this.handleClick });
    });

    const params = {
      client_id: props.clientId,
      redirect_uri: props.redirectUri,
      scope: props.scope,
      display: 'popup',
      response_type: 'token'
    };

    const url = props.authorizationUrl 

    return <div>
      <Popup open={this.state.popupOpen} popupUrl={url} {...props} />
      {childrenWithProps}
    </div>;
  }
}

export default OAuth2;
