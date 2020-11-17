import React from "react";

class ViewStandings extends React.Component {

    constructor() {
        super();
        this.state = {
            records: [],
        }

    }

    getStandings = () => {
        fetch("http://localhost:8080/record/standings")
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    records: data,
                })
            });
    };


    componentDidMount() {
        this.getStandings();
    }




    render() {

        return (
            <>
                <ul className="list-group">
                    {this.state.records.map(record => (
                        <li
                            key={record.userTeamId}
                            className="list-group-item list-group-item-light">
                            {record.userTeamId + ' W-L: ' + record.win + '-' + record.loss}
                        </li>
                    ))}
                </ul>

            </>

        )
    };
}

export default ViewStandings;