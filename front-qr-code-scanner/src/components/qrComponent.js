import React, { Component } from 'react';
import axios from 'axios';

export default class qrComponent extends Component { 

    constructor(props){
        super(props)
        this.state = {
            allQR:[]
        }
    }

    componentDidMount(){
        this.fetchAllQR();
    }

    fetchAllQR() {
        axios('/api/allQR')
          .then(({ data }) => {
            this.setState({ allQR: data });
          })
          .catch((error) => {
            console.log(error.response);
          });
    }

    render(){
        return (
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">ID</th>
                        <th scope="col">uniqCode</th>
                        <th scope="col">name</th>
                        <th scope="col">image</th>
                    </tr>
                </thead>
                <tbody>
                    {this.state.allQR.map((data, i) => {
                        return (
                            <tr key={i}>
                                <th scope="row"><p>{data.id}</p></th>
                                <td><p>{data.uniqCode}</p></td>
                                <td><p>{data.name}</p></td>
                                <td><img width="100" height="100" src={`data:api/allQR/png;base64,${data.image.picByte}`} alt={data.image.filename}/></td>
                            </tr>
                        )
                    })}
                </tbody>
            </table>
        )
    }

}