import React from "react";
import { withRouter } from "react-router";

class SeasonFacts extends React.Component {

    constructor() {
        super();
        this.state = {
            records: [],
            schedules: [],
            userTeam: '',
            win: 0,
            loss: 0,
            size: 3
        }

    };

    componentDidMount() {
        this.getStandings();
        this.getSchedule();
    }

    startNewSeasonOnClickHandler = () => {
        fetch("http://localhost:8080/season/newseason/1");
        this.props.history.push("/PlaySeason");
    }

    startNewLeaugeOnClickHandler = () => {
        fetch("http://localhost:8080/season/newLeague/1");
        this.props.history.push("/TeamsCreation");
    }

    getSchedule = () => {
        fetch("http://localhost:8080/game/schedule/1")
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    schedules: data,
                })
            });
    };


    getStandings = () => {
        fetch("http://localhost:8080/record/standings")
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    records: data,
                })
            });
    };

    render() {
        return (
            <>
                <br />
                <h2 className="text-center font-weight-bold text-danger">Season Summary</h2><br />
                <table className="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Top 3 Teams</th>
                            <th scoope="col">Wins</th>
                            <th scope="col">Losses</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.records.slice(0, this.state.size).map((record, index) => (
                            <tr key={index}>
                                <td>{record.teamName}</td>
                                <td>{record.win}</td>
                                <td>{record.loss}</td>
                            </tr>
                        ))}
                    </tbody>
                </table><br />

                <h2 className="text-center font-weight-bold text-danger">Your Season Results</h2><br />
                <table className="table table-striped">
                    <thead class="thead-dark">
                        <tr>
                            <th scope="col">Game#</th>
                            <th scope="col">Home Team</th>
                            <th scope="col">Away Team</th>
                            <th scope="col">Home Score</th>
                            <th scope="col">Away Score</th>
                            <th scope="col">Win/Loss</th>
                        </tr>
                    </thead>
                    <tbody>
                        {this.state.schedules.map((schedule, index) => (
                            <tr key={index}>
                                <td>{schedule.gameNumber}</td>
                                <td>{schedule.homeTeamName}</td>
                                <td>{schedule.awayTeamName}</td>
                                {schedule.played &&
                                    <td>{schedule.homeScore}</td>
                                }
                                {!schedule.played &&
                                    <td> </td>
                                }
                                {schedule.played &&
                                    <td>{schedule.awayScore}</td>
                                }
                                {!schedule.played &&
                                    <td> </td>
                                }
                                {schedule.played &&
                                    <td>{schedule.result}</td>
                                }
                                {!schedule.played &&
                                    <td> </td>
                                }
                            </tr>
                        ))}
                    </tbody>
                </table><br /><br />

                <div>
                    <button onClick={() => { this.startNewSeasonOnClickHandler() }} className="btn btn-light col-12">Start a New Season</button>
                </div><br />

                <div>
                    <button onClick={() => { this.startNewLeaugeOnClickHandler() }} className="btn btn-light col-12">Start a New League</button>
                </div><br />

            </>
        )
    }
}

export default withRouter(SeasonFacts);

