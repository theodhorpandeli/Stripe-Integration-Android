<?php
define('STRIPE_SECRET_KEY','YOUR_SECRET_API_KEY');
define('STRIPE_PUBLISHABLE_KEY','STRIPE_PUBLISHABLE_KEY');
header('Content-Type: application/json');
$results = array();
require 'vendor/autoload.php';
\Stripe\Stripe::setApiKey(STRIPE_SECRET_KEY);
try{
    $products  = \Stripe\Product::all();
    $results['response'] = "Success";
    $results['products'] = $products->data;

}catch (Exception $e){
    $results['response'] = "Error";
    $results['products'] = $e->getMessage();
}
echo json_encode($results);
