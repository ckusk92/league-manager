import React from "react";
import { withRouter } from "react-router";


class ViewSchedule extends React.Component {

    constructor() {
        super();
        this.state = {
            schedules: [],
        }

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


    componentDidMount() {
        this.getSchedule();
    }


    render() {

        return (
            <>
                <h2 className="text-danger text-center">Season Schedule</h2>
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
                        {this.state.schedules.map((schedule) => (
                            <tr key={schedule.gameId + schedule.homeTeamName}>
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
                </table>

                <button className='btn btn-dark' type="button"
                    onClick={() => { this.props.history.push("/PlaySeason") }}>
                    Season's Page
                </button>

            </>

        )
    };
}

export default withRouter(ViewSchedule);