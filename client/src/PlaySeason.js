import React from "react";
import { withRouter } from "react-router";

class PlaySeason extends React.Component {
    constructor() {
        super();
        this.state = {
            games: [],
            numGames: '',
        }

    };

    componentDidMount() {
        this.getRemainingGames();
    }

    getRemainingGames = () => {
        fetch("http://localhost:8080/game/schedule/1/gamesremaining")
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    numGames: data,
                })
            })
    }

    simGame = () => {
        if (this.state.numGames === 0) {
            this.props.history.push("/SeasonFacts")
        }
        fetch("http://localhost:8080/game/simgame");
        console.log("game simulated");
        this.getRemainingGames();
        console.log(this.state.numGames);
    }

    render() {

        return (
            <>
                <h1 className="text-center"> 2020 Baseball Season</h1><br />
                <div className="form-group">
                    <div className="block-row">
                        <button className="btn btn-light btn-block" type="button"
                            onClick={() => { this.props.history.push("/ViewStandings") }}>
                            View Standings
                </button>
                    </div><br /><br />
                    <div className="form-group-row">
                        <button className="btn btn-light btn-block" type="button"
                            onClick={() => { this.props.history.push("/ViewSchedule") }}>
                            View Schedule
                </button>
                    </div><br /><br />
                    <div className="form-group-row">
                        <button onClick={() => { this.simGame() }} className="btn btn-light btn-block" type="button">
                            Simulate One Game <span class="badge badge-pill badge-danger">{this.state.numGames}</span>
                        </button>
                    </div><br /><br />
                    <div className="form-group-row">
                        <button className="btn btn-light btn-block" type="button">
                            Simulate Remainder of Season <span class="badge badge-pill badge-danger">{this.state.numGames}</span>
                        </button>
                    </div><br /><br />
                </div>
            </>

        );
    }
}

export default withRouter(PlaySeason);