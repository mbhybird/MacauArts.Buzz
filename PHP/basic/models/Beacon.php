<?php

namespace app\models;

use Yii;

/**
 * This is the model class for table "beacon".
 *
 * @property integer $id
 * @property string $beaconid
 * @property string $displayname
 * @property integer $major
 * @property integer $minor
 * @property integer $priority
 * @property integer $effectiverangein
 * @property integer $effectiverangeout
 */
class Beacon extends \yii\db\ActiveRecord
{
    /**
     * @inheritdoc
     */
    public static function tableName()
    {
        return 'beacon';
    }

    /**
     * @inheritdoc
     */
    public function rules()
    {
        return [
            [['major', 'minor', 'priority', 'effectiverangein', 'effectiverangeout'], 'integer'],
            [['beaconid', 'displayname'], 'string', 'max' => 20]
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
            'displayname' => 'Displayname',
            'major' => 'Major',
            'minor' => 'Minor',
            'priority' => 'Priority',
            'effectiverangein' => 'Effectiverangein',
            'effectiverangeout' => 'Effectiverangeout',
        ];
    }
}
