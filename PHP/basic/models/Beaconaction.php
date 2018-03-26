<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "beaconaction".
 *
 * @property integer $id
 * @property string $beaconid
 * @property integer $triggerid
 * @property integer $contentid
 */
class Beaconaction extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'beaconaction';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['triggerid', 'contentid'], 'integer'],
            [['beaconid'], 'string', 'max' => 20]
        ];
    }

    /**
     * @inheritdoc
     */
    public function attributeLabels()
    {
        return [
            'id' => 'ID',
            'beaconid' => 'Beaconid',
            'triggerid' => 'Triggerid',
            'contentid' => 'Contentid',
        ];
    }
}
