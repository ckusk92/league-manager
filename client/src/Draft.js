import React from "react";
import { withRouter } from "react-router";

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
            complete: false,
        };

        this.playerChangeHandler = this.playerChangeHandler.bind(this);

    }

    getfreeAgents = () => {
        fetch("http://localhost:8080/player/freeagents")
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    freeAgents: data,
                })
            });
    };

    getUserTeamPlayers = () => {
        fetch("http://localhost:8080/teamplayer/roster/1")
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
        fetch(`http://localhost:8080/teamplayer/draft`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                'userTeamId': 1,
                'playerId': this.state.playerId
            }),
        }).then((response) => {
            if (response.status === 201) {
                console.log("Success!");
                response.json().then((data) => console.log(data));
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
                <h1 className="text-center bg-primary text-light"> Draft Your Team!</h1><br /><br />

                {!complete && (
                    <form className="form-row">

                        <div className="form-group col-md-5">
                            <div className="select-container">
                                <select className="form-control text-danger font-weight-bold" value={this.state.freeAgent}
                                    onChange={this.playerChangeHandler} >
                                    {this.state.freeAgents.map((freeAgent) =>
                                        <option key={freeAgent.playerId} value={freeAgent.playerId}>
                                            {freeAgent.firstName + " " + freeAgent.lastName + ", " +
                                                freeAgent.position + ", Rating: " + freeAgent.rating}</option>)}
                                </select><br />
                            </div>
                        </div>
                        <div className="form-group col-4">
                            <button className="btn btn-md btn-danger font-weight-bold" type="button"
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

                <div className="float-center col-12 text-xl-center  text-primary font-weight-bold">
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
            </>
        );
    }
}
export default withRouter(Draft);

