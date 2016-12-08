<?php

use Illuminate\Database\Seeder;
use Faker\Factory as Faker;
use Carbon\Carbon ;

class HighscoreTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        //
        $faker = Faker::create();
            	foreach (range(1,100) as $index) {
        	        DB::table('highscore')->insert([
        	            'name' => $faker->name,
        	            'punkte' => $faker->numberBetween(200,1000),
                      'created_at' => Carbon::now()->format('Y-m-d H:i:s'),
                      'updated_at' => Carbon::now()->format('Y-m-d H:i:s'),
        	        ]);
                };
    }
}
