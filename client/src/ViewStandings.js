import React from "react";
import { withRouter } from "react-router";
import AuthContext from './AuthContext';

class ViewStandings extends React.Component {

    constructor() {
        super();
        this.state = {
            records: [],
        }

    }

    getStandings = () => {
        fetch(`${process.env.REACT_APP_API_URL}/record/standings/${this.context.user.appUserId}`)
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
                            <th scope="col">Team</th>
                            <th scoope="col">Wins</th>
                            <th scope="col">Losses</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.records.map((record) => (
                            <tr key={record.userTeamId}>
                                <td>{record.teamName}</td>
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
                <br />
            </>

        )
    };
}

ViewStandings.contextType = AuthContext;
export default withRouter(ViewStandings);