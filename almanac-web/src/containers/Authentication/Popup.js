import React from 'react';
import qs from 'querystring';
import url from 'url';
import Promise from 'bluebird';

class Popup extends React.Component {
  constructor(props) {
    super(props);
  }

  componentDidUpdate() {
    if (this.props.open) {
      this.openPopup();
    }
  }

  openPopup() {
    const props = this.props;
    const width = props.width || 500;
    const height = props.height || 500;

    const options = {
      width: width,
      height: height,
      top: window.screenY + ((window.outerHeight - height) / 2.5),
      left: window.screenX + ((window.outerWidth - width) / 2)
    };

    const popup = window.open(props.popupUrl, '_blank', qs.stringify(options, ','));

    if (props.popupUrl === 'about:blank') {
      popup.document.body.innerHTML = 'Loading...';
    }

    this.pollPopup(popup).then(props.successCallback).catch(props.errorCallback);
  }

  pollPopup(window) {
    const props = this.props;

    return new Promise((resolve, reject) => {
      const redirectUri = url.parse(props.redirectUri);
      const redirectUriPath = redirectUri.host + redirectUri.pathname;

      const polling = setInterval(() => {
        if (!window || window.closed || window.closed === undefined) {
          clearInterval(polling);
          reject(new Error('The popup window was closed'));
        }
        try {
          const popupUrlPath = window.location.host + window.location.pathname;

          if (popupUrlPath === redirectUriPath) {
            if (window.location.search || window.location.hash) {
              const query = qs.parse(window.location.search.substring(1).replace(/\/$/, ''));
              const hash = qs.parse(window.location.hash.substring(1).replace(/[\/$]/, ''));
              const params = Object.assign({}, query, hash);
              if (params.error) {
                reject(new Error(params.error));
              } else {
                resolve(params);
              }
            } else {
              reject(new Error('OAuth redirect has occurred but no query or hash parameters were found.'));
            }
            // cleanup
            clearInterval(polling);
            window.close();
          }
        } catch (error) {
          // Ignore DOMException: Blocked a frame with origin from accessing a cross-origin frame.
          // A hack to get around same-origin security policy errors in Internet Explorer.
        }
      }, 500);
    });
  }

  handleClick() {
    console.log('clicked on button');
  }

  render() {
    return null;
  }
}


export default Popup;
