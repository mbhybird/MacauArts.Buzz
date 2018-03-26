<?php

use yii\helpers\Html;


/* @var $this yii\web\View */
/* @var $model app\models\Beaconaction */

$this->title = 'Create Beaconaction';
$this->params['breadcrumbs'][] = ['label' => 'Beaconactions', 'url' => ['index']];
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="beaconaction-create">

    <h1><?= Html::encode($this->title) ?></h1>

    <?= $this->render('_form', [
        'model' => $model,
    ]) ?>

</div>
