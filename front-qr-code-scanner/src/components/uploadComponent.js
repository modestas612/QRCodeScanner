import React, { Component } from 'react';

class uploadComponent extends Component { 

    constructor(props) {
		super(props);
		this.state = {file: '', name: ''};
	}


    onFileChange = (event) => {
		this.setState({
            file: event.target.files[0]
		});
    }
    
    onNameChange = (event) => {
        this.setState({
            name: event.target.value
		});
    }

    uploadFileData  = (event) => {
        event.preventDefault();
        var data = new FormData();
        data.append('file', this.state.file);
        data.append('name', this.state.name);
    
        fetch('/api/upload', {
			method: 'POST',
			body: data,
        });
        setTimeout(function() {
            document.location.reload()
        }, 2000);
      }

    render(){
        return(
            <div id="container" >
                <form >
                    <h3>Upload QR code image to scan</h3>
                    <div class="form-group">
                        <input onChange={this.onNameChange} type="text" class="form-control" id="fileName" placeholder="QR kodo pavadinimas"/>
                    </div>
                    <div class="form-group">
                        <div class="custom-file">
                            <input onChange={this.onFileChange} type="file" class="custom-file-input" id="customFile"/>
                            <label class="custom-file-label" for="customFile"></label>
                        </div>
                    </div>
                    <button disabled={!this.state.file} class="btn btn-primary" onClick={this.uploadFileData}>Upload</button>
                </form>
                
            </div>
        )
    }

}

export default uploadComponent;