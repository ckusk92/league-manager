import React from "react";


class TeamsCreation extends React.Component {
    constructor() {

        super();
        this.state = {
            teams: [],
            numberOfTeams: 0,
            id: 0,
            teamName: '', //not sure, but including for now
            numberOfGames: 0,
            userTeamChoiceId: 0,
        };
    }

    getTeams = () => {
        fetch("http://localhost:8080/team")
            .then((response) => response.json())
            .then((data) => {
                this.setState({
                    teams: data,
                    id: 0,
                    teamName: '',
                })

            });
    };

    componentDidMount() {
        this.getTeams();
    }

    changeHandler = (event) => {

        const target = event.target;
        const value = target.value;
        const numberOfTeams = target.name;
        const teamName = target.name
        const userTeamChoiceId = target.name

        this.setState({
            [numberOfTeams]: value,
            [teamName]: value,
            [userTeamChoiceId]: value
        });
    };


    createSeasonHandler = (event) => {
        event.preventDefault();

        fetch(`http://localhost:8080/userteam/create`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                numberOfTeams: this.state.numberOfTeams,
                numberOfGames: this.state.numberOfGames,
                userTeamChoiceId: this.state.userTeamChoiceId
            }),
        }).then((response) => {
            if (response.status === 201) {
                console.log("Success!");
                response.json().then((data) => console.log(data));
                this.getTeams();
            } else if (response.status === 400) {
                console.log("Errors!");
                response.json().then((data) => console.log(data));
            } else {
                console.log("oops...");
            }
        });
    };

    render() {
        return (
            <>
                <h2 className="text-center"> Create Season!</h2><br /><br />

                <form>

                    <div className="form-group col-4">
                        <label for="selectNumberOfTeams">Enter the number of teams you to create in your league</label>
                        <input type="number" className="form-control" name="numberOfTeams" value={this.state.numberOfTeams}
                            onChange={this.changeHandler} />
                    </div>

                    <div className="form-group col-4">
                        <label for="selectNumberOfGames">Enter the number of games to play in the season</label>
                        <input type="number" className="form-control" name="numberOfGames" value={this.state.numberOfGames}
                            onChange={this.changeHandler} />
                    </div><br />


                    <div className="dropdown">
                        <button className="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Choose your Team
                    </button>
                        <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <button className="dropdown-item" href="#">Chicago Cubs</button>
                            <button className="dropdown-item" href="#">Cincinatti Reds</button>
                            <button className="dropdown-item" href="#">Chicago White Sox</button>
                        </div>
                    </div><br /><br />

                </form>



                <div>
                    <button type="submit" class="btn btn-block btn-dark col-4">Create Season</button>
                </div>

            </>
        );
    }
}
export default TeamsCreation;

