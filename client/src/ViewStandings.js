import React from "react";
import { withRouter } from "react-router";


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
                <h1 className="text-danger text-center">Season Standings</h1>
                <table className="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Team ID</th>
                            <th scoope="col">Wins</th>
                            <th scope="col">Losses</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.records.map((record) => (
                            <tr key={record.userTeamId}>
                                <td>{record.userTeamId}</td>
                                <td>{record.win}</td>
                                <td>{record.loss}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>

                <button className="btn btn-dark" type="button"
                    onClick={() => { this.props.history.push("/PlaySeason") }}>
                    Season's Page
                </button>

            </>

        )
    };
}

export default withRouter(ViewStandings);