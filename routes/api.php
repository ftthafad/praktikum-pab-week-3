<?php

use App\Http\Controllers\Api\AuthController;
use Illuminate\Support\Facades\Route;

// ✅ Public Routes (tidak butuh token)
Route::prefix('auth')->group(function () {
    Route::post('/register', [AuthController::class, 'register']);
    Route::post('/login',    [AuthController::class, 'login']);
    Route::get('/google',             [AuthController::class, 'redirectToGoogle']);
    Route::get('/google/callback',    [AuthController::class, 'handleGoogleCallback']);
});

// ✅ Protected Routes (butuh token)
Route::middleware('auth:sanctum')->prefix('auth')->group(function () {
    Route::post('/logout', [AuthController::class, 'logout']);
    Route::get('/me',      [AuthController::class, 'me']);
});