import React, { Component } from 'react';

class urlComponent extends Component { 

    constructor(props) {
		super(props);
		this.state = {url: ''};
	}
    
    onUrlChange = (event) => {
        this.setState({
            url: event.target.value
		});
    }

    uploadFileData  = (event) => {
        event.preventDefault();
        var data = new FormData();
        data.append('url', this.state.url);
    
        fetch('/api/imageUrl', {
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
                    <h3>Add url to scan</h3>
                    <div class="form-group">
                        <input onChange={this.onUrlChange} type="text" class="form-control" id="fileName" placeholder="Url"/>
                    </div>
                    <button disabled={!this.state.url} class="btn btn-primary" onClick={this.uploadFileData}>Scan url</button>
                </form>
                
            </div>
        )
    }

}

export default urlComponent;
