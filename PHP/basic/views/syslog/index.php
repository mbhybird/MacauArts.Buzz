<?php

use yii\helpers\Html;
use yii\grid\GridView;

/* @var $this yii\web\View */
/* @var $searchModel app\models\SyslogSearch */
/* @var $dataProvider yii\data\ActiveDataProvider */

$this->title = 'Syslogs';
$this->params['breadcrumbs'][] = $this->title;
?>
<div class="syslog-index">

    <h1><?= Html::encode($this->title) ?></h1>
    <?php // echo $this->render('_search', ['model' => $searchModel]); ?>

    <p>
        <?= Html::a('Create Syslog', ['create'], ['class' => 'btn btn-success']) ?>
    </p>

    <?= GridView::widget([
        'dataProvider' => $dataProvider,
        'filterModel' => $searchModel,
        'columns' => [
            ['class' => 'yii\grid\SerialColumn'],

            'id',
            'userid',
            'beaconid',
            'logtime',
            'triggertype',

            ['class' => 'yii\grid\ActionColumn'],
        ],
    ]); ?>

</div>
