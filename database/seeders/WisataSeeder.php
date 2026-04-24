<?php

namespace Database\Seeders;

use App\Models\Photo;
use App\Models\Wisata;
use Illuminate\Database\Seeder;

class WisataSeeder extends Seeder
{
    public function run(): void
    {
        $wisataData = [
            [
                'user_id'       => 1,
                'category_id'   => 2,
                'name'          => 'Candi Borobudur',
                'description'   => 'Candi Buddha terbesar di dunia yang dibangun pada abad ke-9. Merupakan salah satu keajaiban dunia yang wajib dikunjungi.',
                'location'      => 'Magelang',
                'latitude'      => -7.6079,
                'longitude'     => 110.2038,
                'price'         => 'Rp 50.000',
                'opening_hours' => 'Senin - Minggu: 06.00 - 17.00 WIB',
                'rating'        => 4.9,
                'review_count'  => 120,
                'photos'        => [
                    ['url' => 'https://images.unsplash.com/photo-1596402184320-417e7178b2cd?w=800', 'is_cover' => true],
                    ['url' => 'https://images.unsplash.com/photo-1555400038-63f5ba517a47?w=800', 'is_cover' => false],
                ]
            ],
            [
                'user_id'       => 1,
                'category_id'   => 1,
                'name'          => 'Kawah Dieng',
                'description'   => 'Kawah vulkanik yang menakjubkan di dataran tinggi Dieng dengan pemandangan alam yang luar biasa.',
                'location'      => 'Wonosobo',
                'latitude'      => -7.2097,
                'longitude'     => 109.9167,
                'price'         => 'Rp 20.000',
                'opening_hours' => 'Senin - Minggu: 07.00 - 17.00 WIB',
                'rating'        => 4.7,
                'review_count'  => 85,
                'photos'        => [
                    ['url' => 'https://images.unsplash.com/photo-1555400038-63f5ba517a47?w=800', 'is_cover' => true],
                    ['url' => 'https://images.unsplash.com/photo-1506905925346-21bda4d32df4?w=800', 'is_cover' => false],
                ]
            ],
            [
                'user_id'       => 1,
                'category_id'   => 3,
                'name'          => 'Pantai Parangtritis',
                'description'   => 'Pantai terkenal di Yogyakarta dengan ombak yang besar dan pemandangan sunset yang indah.',
                'location'      => 'Bantul',
                'latitude'      => -8.0257,
                'longitude'     => 110.3322,
                'price'         => 'Rp 10.000',
                'opening_hours' => 'Senin - Minggu: 06.00 - 18.00 WIB',
                'rating'        => 4.5,
                'review_count'  => 95,
                'photos'        => [
                    ['url' => 'https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800', 'is_cover' => true],
                ]
            ],
            [
                'user_id'       => 1,
                'category_id'   => 2,
                'name'          => 'Candi Prambanan',
                'description'   => 'Kompleks candi Hindu terbesar di Indonesia yang dibangun pada abad ke-9 dan merupakan warisan budaya dunia UNESCO.',
                'location'      => 'Sleman',
                'latitude'      => -7.7520,
                'longitude'     => 110.4914,
                'price'         => 'Rp 50.000',
                'opening_hours' => 'Senin - Minggu: 06.00 - 17.00 WIB',
                'rating'        => 4.8,
                'review_count'  => 110,
                'photos'        => [
                    ['url' => 'https://images.unsplash.com/photo-1555400038-63f5ba517a47?w=800', 'is_cover' => true],
                ]
            ],
            [
                'user_id'       => 1,
                'category_id'   => 1,
                'name'          => 'Kebun Teh Kemuning',
                'description'   => 'Hamparan kebun teh yang hijau di lereng Gunung Lawu dengan udara sejuk dan pemandangan yang memukau.',
                'location'      => 'Karanganyar',
                'latitude'      => -7.6271,
                'longitude'     => 111.1399,
                'price'         => 'Rp 15.000',
                'opening_hours' => 'Senin - Minggu: 07.00 - 17.00 WIB',
                'rating'        => 4.6,
                'review_count'  => 72,
                'photos'        => [
                    ['url' => 'https://images.unsplash.com/photo-1501854140801-50d01698950b?w=800', 'is_cover' => true],
                ]
            ],
        ];

        foreach ($wisataData as $data) {
            $photos = $data['photos'];
            unset($data['photos']);

            $wisata = Wisata::create($data);

            foreach ($photos as $photo) {
                Photo::create([
                    'wisata_id' => $wisata->id,
                    'photo_url' => $photo['url'],
                    'is_cover'  => $photo['is_cover'],
                ]);
            }
        }
    }
}