import React from "react";
import { withRouter } from "react-router";
import AuthContext from './AuthContext';

class PlaySeason extends React.Component {
    constructor() {
        super();
        this.state = {
            games: [],
            numGames: '',
            homeTeam: '',
            awayTeam: '',
        }

    };

    componentDidMount() {
        this.getRemainingGames();
    }

    getRemainingGames = () => {
        fetch(`http://localhost:8080/game/schedule/${this.context.user.appUserId}/gamesremaining`)
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    numGames: data,
                })
            })
    }

    simGame = () => {
        if (this.state.numGames < 1) {
            this.props.history.push("/SeasonFacts")
        }
        fetch("http://localhost:8080/game/simgame")
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    games: data,
                });
                console.log("game simulated");
                this.getRemainingGames();
                console.log(this.state.numGames);
            })
    }

    simSeason = () => {
        fetch("http://localhost:8080/game/simseason")
        this.props.history.push("/SeasonFacts")
    }

    render() {

        return (
            <>
                <h1> 2020 Baseball Season</h1><br />
                <div className="form-group">
                    <div className="block-row">
                        <button className="btn btn-dark btn-block" type="button"
                            onClick={() => { this.props.history.push("/ViewStandings") }}>
                            View Standings
                        </button>
                    </div><br /><br />
                    <div className="form-group-row">
                        <button className="btn btn-dark btn-block" type="button"
                            onClick={() => { this.props.history.push("/ViewSchedule") }}>
                            View Schedule
                        </button>
                    </div><br /><br />
                    <div className="form-group-row">
                        <button className="btn btn-dark btn-block" type="button"
                            onClick={() => { this.props.history.push("/ViewFreeAgents") }}>
                            View Free Agents
                        </button>
                    </div><br /><br />
                    <div className="form-group-row">
                        <button onClick={() => { this.simGame() }} className="btn btn-dark btn-block" type="button">
                            Simulate One Game <span className="badge badge-pill">{this.state.numGames + ' remaining'}</span>
                        </button>
                    </div><br /><br />
                    <div className="form-group-row">
                        <button onClick={() => { this.simSeason() }} className="btn btn-dark btn-block" type="button">
                            Simulate Remainder of Season <span className="badge badge-pill">{this.state.numGames + ' remaining'}</span>
                        </button>
                    </div><br /><br />

                    <div>
                        <ul className="list-group col-12 font-weight-bold ">
                            {this.state.games.map(game => (
                                <li key={game.gameId}
                                    className="list-group-item list-group-item-warning text-dark text-center">
                                    {'Game #' + game.gameNumber}<br />
                                    {game.homeTeamName + ': ' + game.homeScore}<br />
                                    {game.awayTeamName + ': ' + game.awayScore}
                                </li>
                            ))}<br />
                        </ul>
                    </div>
                </div>
            </>

        );
    }
}

PlaySeason.contextType = AuthContext;
export default withRouter(PlaySeason);