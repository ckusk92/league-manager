import React from "react";


class TeamsCreation extends React.Component {
	constructor() {
		super();
		this.state = {
			teams: [],
			numberOfTeams: 0,
			teamId: 0,
			name: '', //not sure, but including for now
			numberOfGames: 0,
			userTeamChoiceId: 0,
		};

		this.teamChangeHandler = this.teamChangeHandler.bind(this);

	}

	getTeams = () => {
		fetch("http://localhost:8080/team")
			.then((response) => response.json())
			.then((data) => {
				this.setState({
					teams: data,
				})
			});
	};

	componentDidMount() {
		this.getTeams();
	}

	gamesChangeHandler = (event) => {

		this.setState({
			numberOfGames: event.target.value
		});

	}

	numberOfTeamsChangeHandler = (event) => {

		this.setState({
			numberOfTeams: event.target.value
		});
	}

	teamChangeHandler = (event) => {

		this.setState({
			teamId: event.target.value
		});
	}

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
				userTeamChoiceId: this.state.teamId,
			}),
		}).then((response) => {
			if (response.status === 200) {
				console.log("Success!");
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
				<h1 className="text-center bg-primary text-light"> Create Custom Season</h1><br /><br />

				<form className="form-group row" onSubmit={this.createSeasonHandler}>

					<div className="form-group col-6 text-danger font-weight-bold">
						<label for="selectNumberOfTeams">Enter the number of teams you to create in your league</label>
						<input type="number" className="form-control" name="numberOfTeams" value={this.state.numberOfTeams}
							onChange={this.numberOfTeamsChangeHandler} />
					</div>

					<div className="form-group col-6 text-danger font-weight-bold">
						<label for="selectNumberOfGames">Enter the number of games to play in the season</label>
						<input type="number" className="form-control" name="numberOfGames" value={this.state.numberOfGames}
							onChange={this.gamesChangeHandler} />
					</div><br />

					<div className="form-group col-md-3">
						<div className="select-container">
							<select className="form-control text-danger font-weight-bold" value={this.state.team}
								onChange={this.teamChangeHandler} >
								{this.state.teams.map((team) =>
									<option key={team.teamId} value={team.teamId}>{team.name}</option>)}
					))
						</select><br />
						</div>
					</div>

					<div className="form-group col-12">
						<button type="submit" className="btn btn-primary btn-block font-weight-bold">Create Season</button>
					</div>

				</form>

			</>
		);
	}
}
export default TeamsCreation;

