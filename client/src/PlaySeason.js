import React from "react";

class PlaySeason extends React.Component {
    constructor() {
        super();
    };

    render() {

        return (
            <>
                <h1> 2020 Baseball Season</h1>
                <div className="form-group">
                    <div className="form-group-row">
                        <button type="button">
                            View Standings
                </button>
                    </div>
                    <div>
                        <button type="button">
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

export default PlaySeason;