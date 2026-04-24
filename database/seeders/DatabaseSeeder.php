<?php

namespace Database\Seeders;

use App\Models\User;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\Hash;

class DatabaseSeeder extends Seeder
{
    public function run(): void
    {
        // Buat user dummy dulu
        User::create([
            'name'     => 'Admin',
            'email'    => 'admin@gmail.com',
            'password' => Hash::make('password123'),
            'role'     => 'super_admin',
        ]);

        $this->call([
            CategorySeeder::class,
            WisataSeeder::class,
        ]);
    }
}