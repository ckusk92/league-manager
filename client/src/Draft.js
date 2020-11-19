import React from "react";
import { withRouter } from "react-router";
import AuthContext from './AuthContext';

class Draft extends React.Component {
    constructor() {
        super();
        this.state = {
            freeAgents: [],
            playerId: 0,
            firstName: '',
            lastName: '',
            position: 0,
            rating: 0,
            userTeamId: 1,
            teamPlayers: [],
            draftedPlayers: [],
            complete: false,
        };

        this.playerChangeHandler = this.playerChangeHandler.bind(this);

    }

    getfreeAgents = () => {
        fetch(`http://localhost:8080/player/selectablefreeagents/${this.context.user.appUserId}`)
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    freeAgents: data,
                })
            });
    };

    getUserTeamPlayers = () => {
        fetch(`http://localhost:8080/teamplayer/roster/${this.context.user.appUserId}`)
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    teamPlayers: data,
                    complete: data.length >= 9,
                })
            })
    }

    componentDidMount() {
        this.getfreeAgents();
        this.getUserTeamPlayers();
    }

    playerChangeHandler = (event) => {

        this.setState({
            playerId: event.target.value
        });
    }

    draftPlayerHandler = (event) => {
        fetch(`http://localhost:8080/teamplayer/draft/${this.context.user.appUserId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                'userTeamId': 1,
                'playerId': this.state.playerId
            }),
        }).then((response) => {
            //if (response.status === 201) {
            if (response.status === 200) {
                console.log("Success!");
                response.json().then((data) => {
                    this.setState({
                        draftedPlayers: data
                    })
                });
                this.getUserTeamPlayers();
                // Updates dropdown menu when players are selected
                this.getfreeAgents();
            } else if (response.status === 400) {
                console.log("Errors!");
                response.json().then((data) => console.log(data));
            } else {
                console.log("oops...");
                console.log(this.state.playerId);
            }
        });
    };

    render() {

        const {
            complete,
        } = this.state;

        return (
            <>
                <h1> Draft Your Team!</h1><br /><br />

                {!complete && (
                    <form className="form-row">

                        <div className="form-group col-md-5">
                            <div className="select-container">
                                <select className="form-control text-danger font-weight-bold" value={this.state.playerId}
                                    onChange={this.playerChangeHandler}
                                >
                                    <option value="">-- Select Player --</option>
                                    {this.state.freeAgents.map((freeAgent) =>
                                        <option key={freeAgent.playerId} value={freeAgent.playerId}>
                                            {freeAgent.firstName + " " + freeAgent.lastName + ", " +
                                                freeAgent.position + ", Rating: " + freeAgent.rating}</option>)}
                                </select><br />
                            </div>
                        </div>
                        <div className="form-group col-4">
                            <button className="btn btn-light" type="button"
                                onClick={this.draftPlayerHandler.bind(this)}>
                                Draft Player
                                    </button>
                        </div>
                    </form>
                )}

                {complete && (
                    <div>
                        <h3>Draft Complete</h3>
                        <a className="btn btn-md btn-danger font-weight-bold" onClick={() => this.props.history.push('/PlaySeason')}>Continue</a>
                    </div>
                )}

                <div>
                    Current Roster
                </div><br /><br />

                <div className="text-primary text-center font-weight-bold col-12 ">
                    <ul className="list-group">
                        {this.state.teamPlayers.map(teamPlayer => (
                            <li
                                key={teamPlayer.playerId}
                                className="list-group-item list-group-item-light">
                                {teamPlayer.firstName + ' ' + teamPlayer.lastName + ', ' + teamPlayer.position + '  Rating: ' + teamPlayer.rating}
                            </li>
                        ))}
                    </ul>
                </div>

                <br /><br />
                <div>
                    Previous Round
                </div><br /><br />
                <div className="text-primary text-center font-weight-bold col-12 ">
                    <ul className="list-group">
                        {this.state.draftedPlayers.map(draftedPlayer => (
                            <li
                                key={draftedPlayer.teamName}
                                className="list-group-item list-group-item-light">
                                {draftedPlayer.teamName + ': '}&nbsp;&nbsp;&nbsp;{draftedPlayer.firstName + ' ' + draftedPlayer.lastName + ', ' + draftedPlayer.position + '  Rating: ' + draftedPlayer.rating}
                            </li>
                        ))}
                    </ul>
                </div>
            </>
        );
    }
}

Draft.contextType = AuthContext;
export default withRouter(Draft);

