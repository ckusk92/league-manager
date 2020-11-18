import React from "react";
import { withRouter } from "react-router";

class ViewFreeAgents extends React.Component {
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

    signPlayerHandler = (event) => {
        fetch(`http://localhost:8080/teamplayer/sign`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                'userTeamId': 1,
                'playerId': this.state.playerId
            }),
        }).then((response) => {
            if (response.status === 200) {
                console.log("Success!");
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
        return (
            <>
                <h1>Sign Free Agent</h1><br /><br />
                <br />
                <p>Adding a free agent will cause a corresponding player to be dropped from your team.</p>
                <br /><br />
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
                        <button className="btn btn-dark" type="button"
                            onClick={this.signPlayerHandler.bind(this)}>
                            Acquire Player
                        </button>
                        <button className="btn btn-md btn-dark font-weight-bold" type="button"
                            onClick={() => { this.props.history.push("/PlaySeason") }}>
                            Return to Season
                        </button>
                    </div>
                </form>


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
export default withRouter(ViewFreeAgents);

