import React from "react";
import { withRouter } from "react-router";

class PlaySeason extends React.Component {
    constructor() {
        super();
    };

    render() {

        return (
            <>
                <h1 className="text-center"> 2020 Baseball Season</h1>
                <div className="form-group">
                    <div className="form-group-row">
                        <button type="button"
                            onClick={() => { this.props.history.push("/ViewStandings") }}>
                            View Standings
                </button>
                    </div>
                    <div>
                        <button type="button"
                            onClick={() => { this.props.history.push("/ViewSchedule") }}>
                            View Schedule
                </button>
                    </div>
                    <div>
                        <button type="button">
                            Simulate One Game
                </button>
                    </div>
                    <div>
                        <button type="button">
                            Simulate Remainder of Season
                </button>
                    </div>
                </div>
            </>

        );
    }
}

export default withRouter(PlaySeason);