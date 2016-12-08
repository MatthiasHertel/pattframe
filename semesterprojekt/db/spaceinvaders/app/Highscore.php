<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Highscore extends Model
{
    //
    // set the name because highscore -> highscores no convention
    // does not function
    protected $table = 'highscore';

}
