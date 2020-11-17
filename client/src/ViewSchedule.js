import React from "react";

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
                <h2>This season's schedule</h2>
                <ul className="list-group">
                    {this.state.schedules.map(schedule => (
                        <li
                            key={schedule.gameId}
                            className="list-group-item list-group-item-light">
                            {"Game # " + schedule.gameNumber + ' Away team: ' + schedule.awayTeamId + '@ ' + schedule.homeTeamId +
                                ' *** result(home:away) : ' + schedule.homeScore + ':' + schedule.awayScore}
                        </li>
                    ))}
                </ul>

            </>

        )
    };
}

export default ViewSchedule;