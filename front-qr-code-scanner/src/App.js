import React, { Component } from 'react';
import 'matchmedia-polyfill';
import './App.css';
import 'bootstrap/dist/css/bootstrap.css';
import UploadComponent from './components/uploadComponent';
import UrlComponent from './components/urlComponent';
import QrComponent from './components/qrComponent';
class App extends Component {

  render() {
    return (

      <div className="container">
        <div className="row">
          <div className="col-md-12">
            <div className="form-group files color">
              <h1 class="text-center">QR code scanner</h1>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-md-12">
            <div className="form-group files color">
              <UploadComponent/>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-md-12">
            <div className="form-group files color">
              <UrlComponent/>
            </div>
          </div>
        </div>
        <div className="row">
          <div className="col-md-12">
            <div className="form-group files color">
              <QrComponent/>
            </div>
          </div>
        </div>
      </div>  
    );
  }
}

export default App;
